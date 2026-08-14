import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardGainers } from './dashboard-gainers.component';

const meta: Meta<DashboardGainers> = {
  title: 'Components/DashboardGainers',
  component: DashboardGainers,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardGainers>;

export const Primary: Story = {
  args: {},
};
