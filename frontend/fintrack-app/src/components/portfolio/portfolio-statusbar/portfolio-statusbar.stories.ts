import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioStatusbar } from './portfolio-statusbar.component';

const meta: Meta<PortfolioStatusbar> = {
  title: 'Components/PortfolioStatusbar',
  component: PortfolioStatusbar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioStatusbar>;

export const Primary: Story = {
  args: {},
};
