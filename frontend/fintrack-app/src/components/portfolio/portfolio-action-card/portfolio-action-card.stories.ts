import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioActionCard } from './portfolio-action-card.component';

const meta: Meta<PortfolioActionCard> = {
  title: 'Components/PortfolioActionCard',
  component: PortfolioActionCard,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioActionCard>;

export const Primary: Story = {
  args: {},
};
